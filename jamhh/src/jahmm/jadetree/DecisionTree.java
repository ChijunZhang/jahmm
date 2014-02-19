package jahmm.jadetree;

import jahmm.jadetree.objectattributes.NominalObjectAttribute;
import jahmm.jadetree.objectattributes.ObjectAttribute;
import jutils.designpatterns.CompositeNode;

/**
 *
 * @author kommusoft
 * @param <TSource>
 */
public interface DecisionTree<TSource> extends DecisionNode<TSource>, CompositeNode<DecisionNode<TSource>> {

    Iterable<ObjectAttribute<TSource, Object>> getSourceAttributes();

    void addSourceAttribute(ObjectAttribute<TSource, Object> sourceAttribute);

    void insert(TSource element);

    void expand();

    void reduce();

    boolean checkTrade();

    void trade();

    void tradeExpand();

    void reduceMemory();

    DecisionNode<TSource> getRoot();

    void removeSourceAttribute(ObjectAttribute<TSource, Object> sourceAttribute);

    NominalObjectAttribute<TSource, Object> getTargetAttribute();

    Object classify(TSource element);

    Object classifyInsert(TSource element);

}
